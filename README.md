## GitHub Token Required for Integration Tests

Before running integration tests, you must configure a **GitHub Personal Access Token** in your `application.yml` file.

### How to generate the token

1. Go to [https://github.com/settings/tokens](https://github.com/settings/tokens)
2. Click on **"Generate new token"**
3. Choose appropriate scope

### Add the token to `application.yml`

```yaml
github:
  access-token: ghp_your_generated_token
```
To run all tests, execute the following command in the root directory:

```bash
./mvnw test
```