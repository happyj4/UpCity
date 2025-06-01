/** @type {import('next').NextConfig} */
const nextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/:path*',
        destination: 'http://46.101.245.42/:path*', 
      },
    ];
  },
};

module.exports = nextConfig;
