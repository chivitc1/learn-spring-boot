# Topics
- Rest api
- Spring built-in resource server, authorize server (deprecated)
- Bean valiation
- Error response, exception handling
- Migration using flyway
- Spring profiles
- Unit test, integration test 
- test containers for integration
- doc api using restdocs
- test configurations for unit test
- Upload file using multipart

# Generate api doc

mvn package -Pci

# Run with multiple profiles

$ SPRING_PROFILES_ACTIVE=dev,local mvn spring-boot:run

The dev profile will create drop db, generate create.sql script for migration.

# Create a user

curl --location --request POST 'localhost:8080/api/users' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=6A64C6E44F2849DDCE8B98EC401968C3' \
--data-raw '{
    "email": "user2@example.com",
    "password": "my-secret"
}'

# Authen get token

curl --location --request POST 'http://localhost:8080/oauth/token' \
--header 'Authorization: Basic Y29wLW1vYmlsZS1jbGllbnQ6c2VjcmV0' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=6A64C6E44F2849DDCE8B98EC401968C3' \
--data-urlencode 'username=officer@example.com' \
--data-urlencode 'password=officer' \
--data-urlencode 'grant_type=password' \
--data-urlencode 'client_id=cop-mobile-client' \
--data-urlencode 'client_secret=secret'

# View user detail

curl --location --request GET 'localhost:8080/api/users/me' \
--header 'Authorization: Bearer xgYrTBTcVc+Es725q+SHXhHhJeQ=' \
--header 'Cookie: JSESSIONID=6A64C6E44F2849DDCE8B98EC401968C3'

# Create a report

curl --location --request POST 'localhost:8080/api/reports' \
--header 'Authorization: Bearer 328mM0Xtda7u3XetNsi8qZxlBTQ=' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=6A64C6E44F2849DDCE8B98EC401968C3' \
--data-urlencode 'dateTime=2011-12-03T10:15:30+07:00' \
--data-urlencode 'description=there'\''s a suspect in black hat'