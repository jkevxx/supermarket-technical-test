

### Sale POST
```json
[
{
  "date" : "2026-03-24",
  "status": "completed",
  "idOffice": 1,
  "details": [
    {
      "productName": "Ram 32Gb",
      "amountProd" : 2,
      "price": 4280.0
    },
    {
      "productName": "Ram 64Gb",
      "amountProd" : 1,
      "price": 6800.0
    }
  ]
},
{
  "date" : "2026-04-24",
  "status": "completed",
  "idOffice": 2,
  "details": [
    {
      "productName": "Ram 32Gb",
      "amountProd" : 3,
      "price": 4280.0
    }
  ]
}
]
```

### Sale PUT
```json
{
  "date" : "2026-04-27",
  "status": "completed",
  "idOffice": 2,
  "details": [
    {
      "productName": "Ram 32Gb",
      "amountProd" : 2,
      "price": 4280.0
    }
  ]
}
```

---


## Docker

### Dockerfile
```bash
docker build -t "supermarket-api-image" .

docker run -d --name my-api -p 8080:8080 supermarket-api-image

```

### Dockerfile
```bash
docker-compouse up
```

