# Portfolio landing page

My personal landing page: who I am, what I build and how to reach me. A single-page React application written in TypeScript and styled with Tailwind CSS, bootstrapped with Create React App and deployed to GitHub Pages.

## Structure

```
src/App.tsx                 page layout (header, hero, contact footer)
src/components/Header.tsx   social links and call-to-action buttons
src/components/Home.tsx     hero section; shows an initials avatar until public/profile.jpg exists
src/components/Contact.tsx  contact footer
public/                     static assets and manifest
```

## Run and deploy

```bash
npm install
npm start            # http://localhost:3000
npm run build        # production build in build/
npm run deploy       # publishes build/ to the gh-pages branch (homepage field in package.json)
```

To force a fresh deploy: `rm -rf build node_modules && npm install && npm run build && npm run deploy -- -f`.
