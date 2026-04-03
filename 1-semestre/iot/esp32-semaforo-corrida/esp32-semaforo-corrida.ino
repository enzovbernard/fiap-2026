#define ledVermelho1 15
#define ledVermelho2 2
#define ledVermelho3 4
#define ledVerde 5

#define buzzer 18

void config()
{
  pinMode(ledVermelho1, OUTPUT);
  pinMode(ledVermelho2, OUTPUT);
  pinMode(ledVermelho3, OUTPUT);
  pinMode(ledVerde, OUTPUT);

  ledcAttach(buzzer, 2000, 8);
}

void beep(int freq, int tempo)
{
  ledcWriteTone(buzzer, freq); 
  delay(tempo);
  ledcWriteTone(buzzer, 0);
}

void vermelho1()
{
  digitalWrite(ledVermelho1, HIGH);
  digitalWrite(ledVermelho2, LOW);
  digitalWrite(ledVermelho3, LOW);
  digitalWrite(ledVerde, LOW);

  beep(600, 800);
  delay(1600);
}

void vermelho2()
{
  digitalWrite(ledVermelho1, HIGH);
  digitalWrite(ledVermelho2, HIGH);
  digitalWrite(ledVermelho3, LOW);
  digitalWrite(ledVerde, LOW);

  beep(600, 800);
  delay(1600);
}

void vermelho3()
{
  digitalWrite(ledVermelho1, HIGH);
  digitalWrite(ledVermelho2, HIGH);
  digitalWrite(ledVermelho3, HIGH);
  digitalWrite(ledVerde, LOW);

  beep(600, 800);
  delay(1800);
}

void verde()
{
  digitalWrite(ledVermelho1, LOW);
  digitalWrite(ledVermelho2, LOW);
  digitalWrite(ledVermelho3, LOW);
  digitalWrite(ledVerde, HIGH);

  beep(2000, 1200);
  delay(1400);
}

void setup()
{
  config();
}

void loop()
{
  vermelho1();
  vermelho2();
  vermelho3();
  verde();

  delay(2000);
}