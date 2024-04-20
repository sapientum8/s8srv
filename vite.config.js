import { defineConfig } from 'vite'
import path from "node:path";
import {glob} from "glob";

// https://vitejs.dev/config/
export default defineConfig({
    root: path.join(__dirname, "src/public"),
    publicDir: path.join(__dirname, "src/public"),
    build: {
        outDir: path.join(__dirname, "dist"),
        emptyOutDir: true,
    },
})