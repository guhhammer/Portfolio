export default function Header() {
  return (
    <header className="w-full h-100 absolute flex justify-end items-center p-4">
      <div className="flex space-x-4 items-center">
        {/* Social Links */}
        <a
          href="https://github.com/guhhammer"
          target="_blank"
          rel="noopener noreferrer"
          aria-label="GitHub"
        >
          <svg
            className="w-6 h-6 text-black hover:text-sky-500 transition"
            fill="currentColor"
            viewBox="0 0 24 24"
          >
            <path d="M12 .5C5.73.5.5 5.73.5 12c0 5.1 3.29 9.41 7.86 10.94.58.1.79-.25.79-.56v-2.01c-3.2.69-3.87-1.54-3.87-1.54-.53-1.35-1.29-1.7-1.29-1.7-1.06-.72.08-.71.08-.71 1.17.08 1.79 1.2 1.79 1.2 1.04 1.78 2.73 1.26 3.4.96.1-.75.41-1.26.74-1.55-2.55-.29-5.23-1.28-5.23-5.7 0-1.26.45-2.29 1.19-3.1-.12-.3-.52-1.52.11-3.18 0 0 .98-.31 3.2 1.18A11.14 11.14 0 0 1 12 6.8c.99.01 1.99.13 2.92.39 2.22-1.49 3.19-1.18 3.19-1.18.63 1.66.23 2.88.11 3.18.74.81 1.18 1.84 1.18 3.1 0 4.43-2.69 5.4-5.25 5.68.43.37.81 1.1.81 2.22v3.29c0 .31.2.67.8.56C20.71 21.41 24 17.1 24 12c0-6.27-5.23-11.5-12-11.5z" />
          </svg>
        </a>

        <a
          href="https://www.linkedin.com/in/gustavo-hammerschmidt-577b07188/"
          target="_blank"
          rel="noopener noreferrer"
          aria-label="LinkedIn"
        >
          <svg
            className="w-6 h-6 text-blue-700 hover:text-sky-500 transition"
            fill="currentColor"
            viewBox="0 0 24 24"
          >
            <path d="M4.98 3.5C4.98 4.88 3.88 6 2.5 6S0 4.88 0 3.5 1.12 1 2.5 1 4.98 2.12 4.98 3.5zM.49 8.5h4V24h-4V8.5zM8.49 8.5h3.8v2.1h.05c.53-1 1.83-2.1 3.77-2.1 4.03 0 4.77 2.65 4.77 6.1V24h-4v-6.9c0-1.65-.03-3.78-2.3-3.78-2.3 0-2.65 1.8-2.65 3.66V24h-4V8.5z" />
          </svg>
        </a>

        {/* Buttons */}
        <a
          href="https://github.com/guhhammer/Portfolio/blob/main/CurriculumVitae.pdf"
          className="px-4 py-2 border rounded hover:bg-gray-100 transition"
        >
          See CV
        </a>
        <a
          href="https://github.com/guhhammer/Portfolio"
          className="px-4 py-2 bg-sky-500 text-white rounded hover:bg-sky-600 transition"
        >
          See Portfolio
        </a>
      </div>
    </header>
  );
}
