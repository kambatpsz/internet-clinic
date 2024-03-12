# Internet clinic


## Spis treści

- [Cel i opis projektu](#cel-i-opis-projektu)
- [Użyte technologie](#użyte-technologie)
- [Instrukcja jak rozpocząć pracę z projektem](#instrukcja-jak-rozpocząć-pracę-z-projektem)
- [Autor](#autor)


## Cel i opis projektu

Celem projektu było napisanie aplikacji zgodnie z założeniami zadania kończącego warsztaty Java. Tematem zadania było napisanie aplikacji przychodni internetowej, która ma na celu ułatwienie zarządzania wizytami lekarskimi dla pacjentów oraz umożliwienie lekarzom skutecznego planowania swojego grafiku pracy. Poniżej przedstawione są kluczowe przypadki biznesowe oraz związane z nimi funkcje.
Przypadki biznesowe:

Planowanie Dostępności Lekarzy:
- Lekarze mają możliwość określenia swojej dostępności w aplikacji na potencjalne wizyty.
  -Potencjalny lekarz musi zarejestrować się w systemie, dostarczając niezbędne informacje, takie jak dni pracy itp.

Dodawanie Notatek do Wizyty:
- Lekarze mogą dodawać notatki do wizyt, po spotkaniu z pacjentem, aby utrwalać informacje na temat przebiegu konsultacji.

Sprawdzanie Historii Chorób Pacjenta:
- Lekarze mają dostęp do historii chorób pacjenta, co umożliwia im lepsze zrozumienie kontekstu medycznego.

Rejestracja Pacjenta:
- Pacjenci muszą się zarejestrować w systemie, dostarczając podstawowe informacje, takie jak imię, nazwisko, dane kontaktowe itp.

Umawianie Wizyt:
- Pacjenci mogą umawiać się na wizyty z dostępnymi lekarzami w dostępnych terminach.

Sprawdzanie Historii Wizyt:
- Pacjenci mają dostęp do historii odbytych wizyt oraz informacji dotyczących przyszłych terminów.

Odwoływanie Wizyt:
- Pacjenci mają możliwość odwoływania wcześniej umówionych wizyt.

Przegląd Notatek Lekarza:
- Pacjenci mogą sprawdzić notatki, które lekarz wprowadził w ramach odbytej wizyty.

## Użyte technologie

W projekcie używane są następujące technologie:
- Java
- IntelliJ
- PostgreSQL
- Gradle
- Lombok
- MapStruct
- JUnit 5
- Testcontainers
- UML
- Spring
- Spring Data JPA
- Spring Boot
- Spring MVC
- Spring Security
- Spring Boot REST-API
- Mockito
- Hibernate
- Flyway
- Thymeleaf
- Docker

## Instrukcja jak rozpocząć pracę z projektem

W celu rozpoczęcia pracy z tym projektem należy pobrać projekt, zbudować go (./gradlew clean build -x test) i uruchomić używając dockera compose (docker compose up -d). W przeglądarce internetowej wpisujemy http://localhost:8190/internet-clinic/. Utworzono testowych użytkowników, których można wykorzystać do zapoznania się z aplikacją bez konieczności tworzenia nowych użytkowników w systemie:
- Doktor: login:doctor; hasło:test
- Pacjent: login:patient; hasło:test
- REST API User: REST_API_TEST; hasło:REST_API_TEST

### Autor

Autorem aplikacji jest Kamil Batorski (kam.bat.psz@gmail.com), odpowiedzialny również za rozwój tego projektu oraz wsparcie techniczne dla użytkowników.
