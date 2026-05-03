import http from 'k6/http';
import { check, sleep } from 'k6';
import { Trend, Rate, Counter } from 'k6/metrics';

const responseTime = new Trend('mono_response_time', true);
const errorRate = new Rate('mono_error_rate');
const requestCount = new Counter('mono_request_count');

export const options = {
    stages: [
        { duration: '10s', target: 50 },   // 50 VU로 램프업
        { duration: '30s', target: 50 },   // 50 VU 유지
        { duration: '10s', target: 100 },  // 100 VU로 증가
        { duration: '30s', target: 100 },  // 100 VU 유지
        { duration: '10s', target: 0 },    // 램프다운
    ],
    thresholds: {
        mono_response_time: ['p(95)<2000', 'p(99)<5000'],
        mono_error_rate: ['rate<0.05'],
    },
};

const BASE_URL = 'http://localhost:8080';

// 상품 1~10 중 랜덤 선택
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

    const res = http.post(`${BASE_URL}/api/orders/mono`, payload, params);

    const success = check(res, {
        'status is 200': (r) => r.status === 200,
        'response has orderNo': (r) => {
            try { return JSON.parse(r.body).orderNo !== undefined; }
            catch { return false; }
        },
    });

    responseTime.add(res.timings.duration);
    errorRate.add(!success);
    requestCount.add(1);

    sleep(0.1);
}
