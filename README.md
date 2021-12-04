

# 🚀 1단계 - 인수 테스트 기반 리팩터링

## 요구사항
- [ ] LineSectionAcceptanceTest 리팩터링
- [ ] LineService 리팩터링

### 리팩토링 순서 

### LineService 메소드 정리 
1. 지하철 노선을 저장한다.
2. 지하철 전체 목록을 조회한다. 
3. 지하철 노선 하나를 조회한다. 
4. 지하철 노선을 수정한다. 
5. 지하철 노선을 삭제한다.
6. 지하철 섹션을 추가한다.
7. 지하철 섹션을 삭제한다. 
8. 지하철 역을 조회한다. 
9. 첫번째 역을 조회한다. 

### 영향도 체크 

1. 조회 기능
   - 수정대상 
     - findUpStation
     - getStations
   - 사용범위 
     - saveLine 
     - findLines
     - findLineResponseById 
     - addSection

2. 세션 구간 추가 
   - 수정대상 
     - addLineStation
   - 사용범위 
     - lineService
3. 삭제 
   - 수정대상 
     - removeLineStation
   - 사용범위 
     - lineService
