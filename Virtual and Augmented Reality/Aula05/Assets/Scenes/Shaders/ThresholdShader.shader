Shader "Custom/thresholdShader"
{
    Properties
    {   
        _Threshold ("Thresold", Range(0,255)) = 170
        _UseMean  ("UseMeanAsZero", Range(0,1)) = 1  
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

            float _Threshold;
            int _UseMean;

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

                float limit = _Threshold / 255;

                if (mean < limit && _UseMean == 0){

                    return 0;

                } else if(mean < limit && _UseMean == 1){

                    return mean;

                } else { return 1; }

            }

            ENDCG
        }
        
    }
}
