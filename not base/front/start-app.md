### Vite+React+Tailwind+Typescript

```cmd
npm create vite@latest project_name 
cd project_name
npm install tailwindcss @tailwindcss/vite
```
В vite.config.js вставить:
```javascript
import tailwindcss from "@tailwindcss/vite";
```
В index.css удалить все и вставить:
```cmd
@import "tailwindcss";
```
```cmd
npm install
nom run dev
```

Для стильного фронта: https://ui.shadcn.com/docs/installation
