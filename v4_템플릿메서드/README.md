## 설계 관점
1. 쿠폰과 기프트 카드 등 결제 수단과 관련된 엔티티에 결제 내역으로 변환하는 인터페이스를 제공
2. 각 엔티티에 오버라이드한 메서드를 이용하여 객체 변환

## 설계 패턴; 템플릿 메서드 패턴
- 인터페이스를 구현한 클래스에 `PaymentArchive` 객체로 변환하는 과정을 구현

## 별로인 이유
처음에는 꽤나 괜찮은 아이디어라 생각하고 코드를 작성하고 있었다. 도메인 특성으로 인해서 크리티컬한 문제에 마주하는데..
- 쿠폰, 포인트, 네이버 페이등(서드파티)은 무리없이 적용이 가능하다! 트랜잭션이 단건 발생한다.(서드 파티의 경우에는 주문번호 혹은 제공자가 제공해주는 uuid)
- 기프트 카드의 경우에는 giftCardTrasaction 의 속성을 가져와야한다. 즉 인터페이스 구현을 GiftCard 내에서 하는 경우에는 GiftTrasaction 의 정보를 가져오기 때문에
  1. 매개변수를 통해서 PaymentArchive 의 변수를 지정해주거나
  2. 제네릭 타입으로 데이터를 가져오거나
  3. giftCardTrasaction에 인터페이스 구현체를 만들어주어야한다.

1번으로 하자니 호출 시 모든 매개변수를 명시해야 하므로, 메서드 호출 코드가 더 길고 복잡해진다. 7개 정도. 
2번으로 하자니 차라리 전략 패턴을 쓸것 같다. 혹은 팩토리메서드 패턴. 템플릿 메서드의 장점이 제대로 부각되지 않을것 같다.
3번으로 하자니 주체는 GiftCard 인데 다른 엔티티는 Coupon같은 주체에 가있으면서 GiftCard에만 예외를 두는게 일관적이지 않다고 생각했다.

# 그럼에도 사용해본 이유 V4.1

## 흐름도
<img width="1026" alt="image" src="https://github.com/user-attachments/assets/1c13e1fa-b8c2-4973-97c6-df132055beb3">

## 장점
 1. 다른 결제 수단과 마찬가지로 각 트랜잭션 엔티티가 자신의 변환 로직을 담당헤서 일관된 구조를 유지할 수 있게 해준다.
 2. 변환 로직이 GiftCardTransaction,Coupon 내에 존재함으로써, 트랜잭션 관련 데이터가 필요한 변환 로직의 책임이 분명해진다.
 3. 무엇보다 작성하는 코드의 양이 적어졌다.

## 단점
 1. 앞서 말했듯이 주체인 GiftCard 가 아닌 Transaction 에 주체가 있기에 걱정은 존재한다. 그만큼의 결합도가 상승했다.


하지만 형변환도 존재하지 않고 의존성 분리도 아주 용이해서 고민중에 있다.

# 나름의 합의점 V4.2
## 흐름도
<img width="1032" alt="image" src="https://github.com/user-attachments/assets/966fb586-cef4-48af-a953-c52c8d9e1938">

서비스단 내에 서비스단의 의존성을 가지는것을 방지하고, 트랜잭션 발생시에 들어오는 DTO를 최대한 활용하기 위해서 PaymentArchivable 이라는 인터페이스 내의 메서드에 회원정보(전화번호), 금액, 트랜잭션 타입을 받아와서 구현을 GiftCardTransaction 에서 GiftCard 내부로 옮겼다.

## 장점
1. 앞서 말한 3번 단점이 사라졌다. 인터페이스 구현을 GiftCard에 하게 되어 일관적인 구현이 되었다.


# V4.2 추가 변경사항(의존성 격리)
## 모듈화를 고려한 PaymentArchive 서비스단의 의존성 격리
### 문제 파악
기존에는 GiftCardService나 CouponService단에서 PaymentArchiveService의 save() 메서드를 호출하기 위해 의존성 주입을 통해 구현하였다.
- 쿠폰의 경우에는 상관이 없었지만 기프트 카드의 경우에는 PaymentArchive 엔티티 속성을 위해서 GiftCard, GiftCardTrasaction 을 모두 조회했어야했다.
- 따라서 이 경우에는 의존성 주입이 불가피 하다고 판단되었다.

### 해결방법
명세를 바꾸어서 필요한 GiftCardTrasaction 속성을 RequestDTO 내에 같이 보내달라고 요청하여 GiftCard 도메인 내에 PaymentArchiveService 의존성 주입의 필요성을 해소하였다.

 ### 결론
각 결제 수단이 해당 인터페이스를 구현함으로써 변환 로직을 자체적으로 관리하게 하여, 각 엔티티의 책임과 구조를 명확히 할수있게 되었다.
또한 PaymentArchivable 인터페이스를 통해 다양한 결제 수단의 데이터를 PaymentArchive 객체로 변환하는 과정을 일관되게 처리하게 되었다.

## 흐름도
### 변경 전
PaymentAService가 GiftCardTService 내부에 의존성 주입되어 있어서, GiftCardTransaction 처리와 관련된 로직이 완료된 후 PaymentArchive 객체를 생성하고 저장하는 과정이 이루어졌다. 따라서 GiftCardTransaction 데이터를 필요로 하기 때문에 PaymentAService의 결합도가 높았다.
<img width="900" alt="image" src="https://github.com/user-attachments/assets/189aa81d-a761-48b8-af88-6823546c36d9">

### 변경 후
PaymentAService가 GiftCardController에 주입되어 있어서 API 요청을 받은 직후에 관련 로직을 처리하는 동안 필요한 PaymentArchive 처리를 바로 진행할수 있다. 또한, GiftCard의 처리 로직이 자체적으로 PaymentArchivable 인터페이스를 통해 PaymentArchive로 변환될 수 있게 되기에 데이터 변환과 저장 로직의 흐름이 직접적이고 효율적이다.
<img width="900" alt="image" src="https://github.com/user-attachments/assets/dd137547-fc19-4813-96e5-c0d78ebae609">


추가적으로 이렇게 분리를 해놓을 경우에는 fault tolerance 라고 결함 허용 설계가 가능해져서 누락되거나 속도에 민감한 Transaction을 우선적으로 처리하고 PaymentArchive는 여러번 재시도를 통해 처리하는 설계 방식도 도입하기 용이해진다.
