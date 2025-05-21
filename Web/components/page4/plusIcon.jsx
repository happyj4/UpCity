export function IconPlus() {
  return (
    <svg
      className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2"
      width="73"
      height="73"
      viewBox="0 0 73 73"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <g filter="url(#filter0_d_349_443)">
        <path
          d="M69 40.2857H40.2857V69H30.7143V40.2857H2V30.7143H30.7143V2H40.2857V30.7143H69V40.2857Z"
          fill="#E1DFDC"
        />
      </g>
      <defs>
        <filter
          id="filter0_d_349_443"
          x="0.7"
          y="0.7"
          width="71.6"
          height="71.6"
          filterUnits="userSpaceOnUse"
          colorInterpolationFilters="sRGB"
        >
          <feFlood floodOpacity="0" result="BackgroundImageFix" />
          <feColorMatrix
            in="SourceAlpha"
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0"
            result="hardAlpha"
          />
          <feOffset dx="1" dy="1" />
          <feGaussianBlur stdDeviation="1.15" />
          <feComposite in2="hardAlpha" operator="out" />
          <feColorMatrix
            type="matrix"
            values="0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.25 0"
          />
          <feBlend
            mode="normal"
            in2="BackgroundImageFix"
            result="effect1_dropShadow_349_443"
          />
          <feBlend
            mode="normal"
            in="SourceGraphic"
            in2="effect1_dropShadow_349_443"
            result="shape"
          />
        </filter>
      </defs>
    </svg>
  );
}
