Shader "Custom/TexShader"
{
    Properties
    {
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

                float mean = (0.3 * color[0] + 0.59 * color[1] + 0.11 * color[2]);

                return mean;

            }

            ENDCG
        }
        
    }
}
