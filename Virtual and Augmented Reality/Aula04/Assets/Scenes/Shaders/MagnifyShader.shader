Shader "Custom/MagnifyShader"
{
    Properties
    {   
        _Magnify ("Magnify", Range(0.0,10.0)) = 1.0
        _MainTex ("Texture", 2D) = "white" {}
    }
    SubShader
    {
        Pass
        {
            CGPROGRAM
            
            #pragma vertex vert;
            #pragma fragment frag;

            uniform sampler2D _MainTex;


            float _Magnify;


            struct VertexInput
            {

                float4 vertex : POSITION;
                float4 texCoord : TEXCOORD0;

            };

            struct VertexOutput
            {

                float4 pos : SV_POSITION;
                float4 texCoord : TEXCOORD0;

            };            

            VertexOutput vert(VertexInput input)
            {

                VertexOutput output;
                output.pos = UnityObjectToClipPos(input.vertex);
                output.texCoord = input.texCoord;
                return output;

            }

            float4 frag(VertexOutput input) : COLOR
            {

                float4 color =  tex2D(_MainTex, input.texCoord.xy);

                color[0] = _Magnify * color[0];
                color[1] = _Magnify * color[1];
                color[2] = _Magnify * color[2];

                return color;

            }

            ENDCG
        }
        
    }
}
