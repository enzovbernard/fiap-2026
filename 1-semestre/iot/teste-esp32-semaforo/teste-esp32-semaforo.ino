#define ledVermelho 15
#define ledAmarelo 2
#define ledVerde 4

#define buzzer 5

void config()
{
  pinMode(ledVermelho, OUTPUT);
  pinMode(ledAmarelo, OUTPUT);
  pinMode(ledVerde, OUTPUT);
  pinMode(buzzer, OUTPUT);
}

void vermelho()
{
  digitalWrite(ledVermelho, HIGH);
  digitalWrite(ledAmarelo, LOW);
  digitalWrite(ledVerde, LOW);

  tone(buzzer, 600); 
  delay(800);        
  noTone(buzzer);

  delay(1600);
}  

void amarelo()
{
  digitalWrite(ledVermelho, LOW);
  digitalWrite(ledAmarelo, HIGH);
  digitalWrite(ledVerde, LOW);  
  
  tone(buzzer, 800);   
  delay(800);        
  noTone(buzzer);

  delay(1200); 

}

void verde()
{
  digitalWrite(ledVermelho, LOW);
  digitalWrite(ledAmarelo, LOW);
  digitalWrite(ledVerde, HIGH);  

  tone(buzzer, 2000);
  delay(1200);        
  noTone(buzzer);

  delay(1400); 
}

void setup()
{
  config();
}

void loop()
{
  vermelho();
  amarelo();
  verde();

  delay(2000);
}