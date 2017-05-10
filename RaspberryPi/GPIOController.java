import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class GPIOController {


/*
*GPIO���� �̿��Ͽ� 2���� ���͸� ����ϸ鼭
*�յ� �¿츦 �����Ѵ�.
*ENA_PIN,ENB_PIN���� ���� dc���� ����̺긦 ����Ҷ� ������������HIGH���� ��� 
*�������� ���� �������ش�.
*�Ʒ��� �� ���� �����̴�.
*/
   GpioController gpio = GpioFactory.getInstance();
   GpioPinDigitalOutput GO_PIN;
   GpioPinDigitalOutput BACK_PIN;
   GpioPinDigitalOutput LEFT_PIN;
   GpioPinDigitalOutput RIGHT_PIN;
   GpioPinDigitalOutput ENA_PIN;
   GpioPinDigitalOutput ENB_PIN;


/*
* �Ʒ��� GPIOController��� �����ڸ� �����������,
* �� ���� �ʱ�ȭ�ϴµ� GPIO_00�� ���Ҽ� �ִ� ���̸�, ���� 00,02,03,04�� �ִ´�.
* �׸��� ENA, ENB�� PWM������ �ʱ�ȭ���־���.
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


//�� �޼ҵ�� ���� ���� ���� KeyDown,GO �̷����ε�, trim�� ����Ͽ��� ���� ������ ������,
//�װſ� ���� ���� switch���� �̿��ؼ� �����̴� ���� �����ϰ� �ߴ�.
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


//�ʱ�ȭ �޼ҵ�� ���������̴�.
   public void AccelReset() {
      GO_PIN.low();
      BACK_PIN.low();
   }
//�ʱ�ȭ �޼ҵ�� ���⸮���̴�.
   public void DirectReset() {
      LEFT_PIN.low();
      RIGHT_PIN.low();
   }
//�ʱ�ȭ �޼ҵ�� ��ü �����̴�.
   public void reset() {
      GO_PIN.low();
      BACK_PIN.low();
      LEFT_PIN.low();
      RIGHT_PIN.low();
   }
//�������� �����̴� �޼ҵ�
   public void left() {
      LEFT_PIN.high();
      RIGHT_PIN.low();
   }
//���������� �����̴� �޼ҵ�
   public void right() {
      RIGHT_PIN.high();
      LEFT_PIN.low();
   }
//������ �����̴� �޼ҵ�
   public void go() {
      GO_PIN.high();
      BACK_PIN.low();
   }
//�������� �����̴� �޼ҵ�
   public void back() {
      GO_PIN.low();
      BACK_PIN.high();
   }
}