### JSON Server

npm install json-server

Create a db.json or db.json5 file
```json
{
  "posts": [
    { "id": "1", "title": "a title", "views": 100 },
    { "id": "2", "title": "another title", "views": 200 }
  ],
  "comments": [
    { "id": "1", "text": "a comment about post 1", "postId": "1" },
    { "id": "2", "text": "another comment about post 1", "postId": "1" }
  ],
  "profile": {
    "name": "typicode"
  }
}
```

npx json-server db.json

```cmd
$ curl http://localhost:3000/posts/1
```
```json
{
  "id": "1",
  "title": "a title",
  "views": 100
}
```