# 서론
<img width="1338" alt="image" src="https://github.com/user-attachments/assets/89f7946b-2876-49d5-9f0f-7a7d0a27058e">

대금 결제 내역을 관리할 때, 백엔드 시스템에서는 '전자상거래 등에서의 소비자 보호에 관한 법률'에 따라 5년 동안 해당 정보를 보관해야한다. 따라서 5년간의 내용저장과 기간이 지난 데이터는 자동으로 삭제되도록 시스템을 구성해야하는데 그 중 내용 저장에 관련한 api에 중점적으로 작성해보았다.

 현재 실운용되고 있는 결제 도메인으로는 자체적으로 구현한 쿠폰(기프티콘)과 충전식 선불카드(기프트카드)가 존재한다.

# 도메인 구성

## **Payment 관련 Domain**

Coupon과 GiftCard와 같은 결제와 관련된 엔티티들이 포함된 도메인

  ### **Coupon**
  - 쿠폰은 발급과 사용 시 최대 2회의 트랜잭션을 발생시킬 수 있다.
  - 즉, 쿠폰의 경우 제한된 횟수의 트랜잭션만 생성되며, 발급 또는 사용(만료)시에 추가된다.
  
  ### **GiftCard**
  - GiftCard 엔티티는 여러 개의 트랜잭션과 연결되어 있으며, 재충전이 가능하므로 n회의 트랜잭션이 발생할 수 있다.
  - 기프트 카드가 반복적으로 사용할 수 있고, 사용할 때마다 새로운 트랜잭션이 생성된다.

  ### 추후에 추가될 결제api

## **Transaction Domain**
- GiftCard가 각각 수행하는 거래(트랜잭션)들이 포함된 도메인

## **PaymentArchive Domain**
- 개인정보보호법에 의한 결제내역 저장을 위한 도메인

# 본론

## 결제수단_결제_결제내역 저장 케이스
### [전략 패턴 + 레지스트리 패턴](https://github.com/ChoMinGi/DeepDive-designPattern/tree/main/v1_%EC%A0%84%EB%9E%B5%2B%EB%A0%88%EC%A7%80%EC%8A%A4%ED%84%B0)
### [전략 패턴 + (단순)팩토리 패턴](https://github.com/ChoMinGi/DeepDive-designPattern/tree/main/v2_%EC%A0%84%EB%9E%B5%2B%ED%8C%A9%ED%86%A0%EB%A6%AC)
### [팩토리 메서드 패턴](https://github.com/ChoMinGi/DeepDive-designPattern/tree/main/v3_%ED%8C%A9%ED%86%A0%EB%A6%AC%EB%A9%94%EC%84%9C%EB%93%9C)
### [템플릿 메서드 패턴](https://github.com/ChoMinGi/DeepDive-designPattern/tree/main/v4_%ED%85%9C%ED%94%8C%EB%A6%BF%EB%A9%94%EC%84%9C%EB%93%9C)
