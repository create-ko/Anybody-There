import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GPIOController {


/*
*GPIO핀을 이용하여 2가지 모터를 사용하면서
*앞뒤 좌우를 조절한다.
*ENA_PIN,ENB_PIN같은 경우는 dc모터 드라이브를 사용할때 무조건적으로HIGH값을 줘야 
*모터한테 값을 전달해준다.
*아래는 핀 변수 생성이다.
*/
   GpioController gpio = GpioFactory.getInstance();
   GpioPinDigitalOutput GO_PIN;
   GpioPinDigitalOutput BACK_PIN;
   GpioPinDigitalOutput LEFT_PIN;
   GpioPinDigitalOutput RIGHT_PIN;
   GpioPinDigitalOutput ENA_PIN;
   GpioPinDigitalOutput ENB_PIN;


/*
* 아래는 GPIOController라는 생성자를 만들어줬으며,
* 각 핀을 초기화하는데 GPIO_00은 변할수 있는 값이며, 보통 00,02,03,04로 넣는다.
* 그리고 ENA, ENB는 PWM핀으로 초기화해주었다.
*/

   public GPIOController() {
      //Initialize GPIOFactory
      gpio = GpioFactory.getInstance();
      GO_PIN = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "GO_PIN", PinState.LOW);
      BACK_PIN = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "BACK_PIN", PinState.LOW);
      LEFT_PIN = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "LEFT_PIN", PinState.LOW);
      RIGHT_PIN = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"RIGHT_PIN", PinState.LOW);
      ENA_PIN = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26,"ENA_PIN", PinState.HIGH);
      ENB_PIN = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23,"ENB_PIN", PinState.HIGH);
   }


//이 메소드는 이제 들어온 값이 KeyDown,GO 이런식인데, trim을 사용하여서 값을 구분한 다음에,
//그거에 대한 값을 switch문을 이용해서 움직이는 것이 가능하게 했다.
   public void control(String msg) {
      String[] data = msg.split(",");

      if (data[0].equals("KeyDown")) {
         // Below code is execute when press the button.
         switch (data[1]) {
         case "GO":
            AccelReset();
            go();
            break;
         case "BACK":
            AccelReset();
            back();
            break;
         case "Left":
            DirectReset();
            left();
            break;
         case "Right":
            DirectReset();
            right();
            break;
         }
      } else if (data[0].equals("KeyUp")) {
         // Below code is execute when stop press the button.
         switch (data[1]) {
         case "GO":
         case "BACK":
            AccelReset();
            break;
         case "Left":
         case "Right":
            DirectReset();
            break;
         }

      }
   }


//초기화 메소드로 전진리셋이다.
   public void AccelReset() {
      GO_PIN.low();
      BACK_PIN.low();
   }
//초기화 메소드로 방향리셋이다.
   public void DirectReset() {
      LEFT_PIN.low();
      RIGHT_PIN.low();
   }
//초기화 메소드로 전체 리셋이다.
   public void reset() {
      GO_PIN.low();
      BACK_PIN.low();
      LEFT_PIN.low();
      RIGHT_PIN.low();
   }
//왼쪽으로 움직이는 메소드
   public void left() {
      LEFT_PIN.high();
      RIGHT_PIN.low();
   }
//오른쪽으로 움직이는 메소드
   public void right() {
      RIGHT_PIN.high();
      LEFT_PIN.low();
   }
//앞으로 움직이는 메소드
   public void go() {
      GO_PIN.high();
      BACK_PIN.low();
   }
//뒤쪽으로 움직이는 메소드
   public void back() {
      GO_PIN.low();
      BACK_PIN.high();
   }
}