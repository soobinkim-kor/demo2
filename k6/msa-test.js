import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate, Counter } from 'k6/metrics';

const responseTime = new Trend('msa_response_time', true);
const errorRate = new Rate('msa_error_rate');
const requestCount = new Counter('msa_request_count');

export const options = {
    stages: [
        { duration: '10s', target: 50 },
        { duration: '30s', target: 50 },
        { duration: '10s', target: 100 },
        { duration: '30s', target: 100 },
        { duration: '10s', target: 0 },
    ],
    thresholds: {
        msa_response_time: ['p(95)<2000', 'p(99)<5000'],
        msa_error_rate: ['rate<0.05'],
    },
};

const BASE_URL = 'http://localhost:8080';

function randomProductNo() {
    return Math.floor(Math.random() * 10) + 1;
}

export default function () {
    const productNo = randomProductNo();

    const payload = JSON.stringify({
        usrNo: Math.floor(Math.random() * 1000) + 1,
        items: [
            {
                productNo: productNo,
                productNm: `상품-${productNo}`,
                quantity: 1,
                unitPrice: 10000,
            },
        ],
    });

    const params = {
        headers: { 'Content-Type': 'application/json' },
        timeout: '10s',
    };

    // MSA는 PENDING 상태로 즉시 응답 (비동기 처리)
    const res = http.post(`${BASE_URL}/api/orders`, payload, params);

    const success = check(res, {
        'status is 200': (r) => r.status === 200,
        'order status is PENDING': (r) => {
            try { return JSON.parse(r.body).orderStatus === 'PENDING'; }
            catch { return false; }
        },
    });

    responseTime.add(res.timings.duration);
    errorRate.add(!success);
    requestCount.add(1);

    sleep(0.1);
}
