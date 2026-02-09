import React from "react";
import profileImage from "../assets/profile.jpg";

export default function Home() {
 return (
  <section className="w-full min-h-screen flex flex-col md:flex-row bg-white dark:bg-gray-900 p-8 md:p-20">
    {/* Text Side - Left (but centered in its half) */}
    <div className="w-full md:w-1/2 flex justify-center">
    <div className="my-auto max-w-md space-y-6 text-center md:text-left">
      <p className="text-pink-500 font-medium text-lg">📍 Curitiba, PR, Brasil</p>
      <h1 className="text-4xl md:text-5xl font-bold leading-tight">
        Web3 & Full-Stack <br />
        <span className="text-sky-500">Developer</span> <br />
        Building Scalable <br />
        <span className="text-sky-500">Blockchain Solutions</span>
      </h1>
      <p className="text-gray-600 dark:text-gray-300 text-lg">
        Proficient in Solidity, Rust, React, and more. I create custom dApps with a strong foundation in economics and code.
      </p>
    </div>
  </div>

    {/* Image Side - Right */}
    <div className="w-full md:w-1/2 flex justify-center items-center relative mt-12 md:mt-0">
      {/* Purple Circle Behind Image */}
      <div className="absolute w-[300px] h-[300px] md:w-[400px] md:h-[400px] bg-purple-100 rounded-full -z-10" />

      {/* Profile Image */}
      <div className="w-[300px] h-[300px] md:w-[400px] md:h-[400px]
        rounded-full overflow-hidden border-4 border-white shadow-lg 
        bg-blue-100 select-none pointer-events-none">
        <img
          src={profileImage}
          alt="Profile"
          className="w-full h-full object-cover"
          draggable="false"
          onContextMenu={(e) => e.preventDefault()}
        />
      </div>
    </div>
  </section>
);
}