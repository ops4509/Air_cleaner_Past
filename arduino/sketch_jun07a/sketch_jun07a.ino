#include <SoftReset.h>
#include <Stepper.h>

const int stepsPerRevolution = 1024;
Stepper myStepper(stepsPerRevolution, 11, 12, 10, 8);

int measurePin = A0;
int ledPower = 2;

int samplingTime = 280;
int deltaTime = 40;
int sleepTime = 9680;

float voMeasured = 0.0;
float calcVoltage = 0.0;
float dustDensity = 0.0;

int Relaypin = 3;
int Switch = 9;
String income = "Relay On";
String outcome = "Relay Off";

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  pinMode(ledPower, OUTPUT);

  myStepper.setSpeed(30);

  pinMode(Relaypin, OUTPUT);
  pinMode(Switch, INPUT_PULLUP);
}

void loop() {
  // put your main code here, to run repeatedly:
  digitalWrite(ledPower, LOW);
  delayMicroseconds(samplingTime);

  voMeasured = analogRead(measurePin);

  delayMicroseconds(deltaTime);
  digitalWrite(ledPower, HIGH);
  delayMicroseconds(sleepTime);

  calcVoltage = voMeasured * (5.0 / 1024.0);

  dustDensity = 0.17 * calcVoltage - 0.1;

  //Serial.print("-Voltage: ");
  Serial.print(calcVoltage);
  Serial.print(" ");
  //Serial.print(" - Dust Density: ");
  Serial.print(dustDensity);
  Serial.print(" ");

  if (dustDensity > 0.30) {
    Serial.print(income);
    digitalWrite(Relaypin, HIGH);

    myStepper.step(stepsPerRevolution);

    myStepper.step(-stepsPerRevolution);
    delay(10);
  }
  else {
    Serial.print(outcome);
    digitalWrite(Relaypin, LOW);
    delay(3000);
  }

  Serial.println();

}
