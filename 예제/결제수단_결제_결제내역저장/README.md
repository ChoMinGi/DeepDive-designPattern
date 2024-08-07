# 도메인 구분

## Pay 관련 Domain
Coupon과 GiftCard와 같은 결제와 관련된 엔티티들이 포함된 도메인
### Coupon
- Coupon 엔티티는 Transaction과 매핑되어있다.
- 쿠폰은 발급과 사용 시 최대 2회의 트랜잭션을 발생시킬 수 있다.
- 즉, 쿠폰의 경우 제한된 횟수의 트랜잭션만 생성되며, 발급 또는 사용(만료)시에 추가된다.

### GiftCard
- GiftCard 엔티티는 여러 개의 트랜잭션과 연결되어 있으며, 재충전이 가능하므로 n회의 트랜잭션이 발생할 수 있다.
- 기프트 카드가 반복적으로 사용할 수 있고, 사용할 때마다 새로운 트랜잭션이 생성된다.

## Transaction Domain
Coupon과 GiftCard가 각각 수행하는 거래(트랜잭션)들이 포함된 도메인

## PaymentArchive Domain
개인정보보호법에 의한 결제내역 저장을 위한 도메인

### 결제 내역 전략 패턴
PaymentArchiveStrategy가 결제 내역 저장과 관련된 전략 패턴의 역할을 수행