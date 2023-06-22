const path = require("path");
const nodeExternals = require("webpack-node-externals");

module.exports = {
    mode: "production",
    entry: "./src/app.ts",
    target: "node",
    externalsPresets: {
        node: true
    },
    externals: [nodeExternals()],
    output: {
        filename: "app.js",
        path: path.resolve(__dirname, "dist")
    },
    resolve: {
        extensions: [".ts"],
    },
    module: {
        rules:[{
            test: /\.ts?$/,
            use: [{
                loader: "ts-loader",
                options: {
                    transpileOnly: true
                }
            }]
        }]
    }
};