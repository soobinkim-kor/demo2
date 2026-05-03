#!/bin/bash

echo "=============================="
echo "스트레스 테스트 비교 시작"
echo "=============================="

# 테스트 데이터 초기화
echo "[1/4] 모놀리식 재고 초기화..."
curl -s -X POST http://localhost:8080/api/orders/mono/init
echo ""

# MSA(product-service) 재고 초기화는 product-service에 별도 init API 필요
# curl -s -X POST http://localhost:8081/api/inventory/init

echo ""
echo "[2/4] 모놀리식 스트레스 테스트 실행..."
k6 run --out json=mono-result.json mono-test.js

echo ""
echo "[3/4] MSA 스트레스 테스트 실행..."
k6 run --out json=msa-result.json msa-test.js

echo ""
echo "[4/4] 결과 요약"
echo "=============================="
echo "mono-result.json / msa-result.json 확인"
echo ""
echo "주요 비교 지표:"
echo "  - mono_response_time (p95, p99)"
echo "  - msa_response_time  (p95, p99)"
echo "  - mono_error_rate"
echo "  - msa_error_rate"
echo "  - mono_request_count (총 처리량)"
echo "  - msa_request_count  (총 처리량)"
