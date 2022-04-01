# main.{dest} Rscocket endpoint

Rsocket endpoint which accepts websocket messages with payload encrypted using Hessian protocol
payload should be of type SimpleMessage. This type consists of:
```properties
value: String
id: UUID
```