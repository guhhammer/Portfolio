import React from "react";

// Drop a photo at public/profile.jpg to show it; without one the page renders
// an initials avatar so a fresh clone always builds and runs.
const profileImage = `${process.env.PUBLIC_URL}/profile.jpg`;
const hasPhoto = false; // flip to true once public/profile.jpg exists

export default function Home() {
 return (
  <section className="w-full min-h-screen flex flex-col md:flex-row bg-white dark:bg-gray-900 p-8 md:p-20">
    {/* Text side */}
    <div className="w-full md:w-1/2 flex justify-center">
    <div className="my-auto max-w-md space-y-6 text-center md:text-left">
      <p className="text-pink-500 font-medium text-lg">📍 Curitiba, Brazil</p>
      <h1 className="text-4xl md:text-5xl font-bold leading-tight">
        Backend Engineer <br />
        <span className="text-sky-500">Rust · Python · TypeScript</span> <br />
        Distributed Systems <br />
        <span className="text-sky-500">& Blockchain</span>
      </h1>
      <p className="text-gray-600 dark:text-gray-300 text-lg">
        I build backend services and the tooling around them: REST APIs in Rust, data and automation in Python,
        web apps in TypeScript, smart contracts in Solidity, all shipped with Docker. Computer Science graduate with a
        strong grasp of economics.
      </p>
    </div>
  </div>

    {/* Image side */}
    <div className="w-full md:w-1/2 flex justify-center items-center relative mt-12 md:mt-0">
      {/* Purple circle behind the image */}
      <div className="absolute w-[300px] h-[300px] md:w-[400px] md:h-[400px] bg-purple-100 rounded-full -z-10" />

      {/* Profile image, or an initials avatar when no photo is bundled */}
      <div className="w-[300px] h-[300px] md:w-[400px] md:h-[400px]
        rounded-full overflow-hidden border-4 border-white shadow-lg
        bg-blue-100 select-none pointer-events-none">
        {hasPhoto ? (
          <img
            src={profileImage}
            alt="Profile"
            className="w-full h-full object-cover"
            draggable="false"
            onContextMenu={(e) => e.preventDefault()}
          />
        ) : (
          <div className="w-full h-full flex items-center justify-center bg-sky-100">
            <span className="text-8xl font-bold text-sky-500">GH</span>
          </div>
        )}
      </div>
    </div>
  </section>
);
}
