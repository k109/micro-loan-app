# micro-loan-app

Wymagania biznesowe

- Klient może wnioskować o pożyczkę wysyłając kwotę oraz termin

- Dokonana jest analiza wniosku pod względem ryzyka. Aplikacja jest uznana za ryzykowną jeśli:

 - wniosek jest pomiędzy 0:00 a 6:00 rano i wniosek jest na kwotę maksymalną

 - trzykrotne wnioskowanie z tego samego adresu IP

- pożyczka jest przyznana jeśli przechodzi przez proces ryzyka bez błędów

- w trakcie trwania umowy o pożyczkę, klient może jednorazowo wnioskować o odroczenie terminu spłaty (przedłużenie pożyczki) o 14 dni.