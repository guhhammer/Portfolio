Shader "Custom/ShaderPixelate"
{
    Properties
    {

        _MainTex ("Texture", 2D) = "white" {}
        _PixelSize("Tamanho do Pixel", Int) = 80

    }
    SubShader
    {        
        Pass
        {
        
            CGPROGRAM

            #pragma vertex vert
            #pragma fragment frag

            #include "UnityCG.cginc"
            
            uniform float4 _MainTex_TexelSize;

            sampler2D _MainTex;
            int _PixelSize;
        
            struct appdata
            {
                float4 vertex : POSITION;
                float2 uv : TEXCOORD0;
            };

            struct v2f
            {
                float2 uv : TEXCOORD0;
                float4 vertex : SV_POSITION;
            };

            v2f vert (appdata v)
            {
                v2f o;
                o.vertex = UnityObjectToClipPos(v.vertex);
                o.uv = v.uv;
                return o;
            }

            fixed4 frag(v2f i) : COLOR
            {       

                return tex2D(_MainTex, round(i.uv * float(_PixelSize)) / float(_PixelSize) );
                
            }

            ENDCG

        }

    }

}
