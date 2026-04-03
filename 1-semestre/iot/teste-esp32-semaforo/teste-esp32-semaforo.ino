#define ledVermelho 15
#define ledAmarelo 2
#define ledVerde 4




void config()
{
  pinMode(ledVermelho, OUTPUT);
  pinMode(ledAmarelo, OUTPUT);
  pinMode(ledVerde, OUTPUT);
}

void vermelho()
{
  digitalWrite(ledVermelho, HIGH);
  digitalWrite(ledAmarelo, LOW);
  digitalWrite(ledVerde, LOW);
  delay(4000);
}

void amarelo()
{
  digitalWrite(ledVermelho, LOW);
  digitalWrite(ledAmarelo, HIGH);
  digitalWrite(ledVerde, LOW);  
  delay(2000);
}

void verde()
{
  digitalWrite(ledVermelho, LOW);
  digitalWrite(ledAmarelo, LOW);
  digitalWrite(ledVerde, HIGH);  
  delay(4000);  
}


void setup()
{
  config();
}

void loop()
{
  verde();
  amarelo();
  vermelho();
}