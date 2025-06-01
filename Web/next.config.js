/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://46.101.245.42:8000/:path*', 
      },
    ];
  },
};

module.exports = nextConfig;
