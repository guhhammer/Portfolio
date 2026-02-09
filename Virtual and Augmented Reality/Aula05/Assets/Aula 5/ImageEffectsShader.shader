Shader "Custom/ImageEffectsShader"
{
    Properties
    {
        _MainTex ("Image", 2D) = "white" {}
        [Enum(None,0,BWRed,1,BWMedian,2,BWFormula,3)] _Effect("Effect",Int) = 0
        _Bright("Bright",Range(0.0,10.0)) = 1.0
        [Toggle] _UseThreshold("Use Threshold",Int) = 1
        _Threshold("Threshold",Range(0,1)) = 0
    }
    SubShader
    {Pass{
        CGPROGRAM
        #pragma vertex vert
        #pragma fragment frag

        uniform sampler2D _MainTex;
        int _Effect,_UseThreshold;
        float _Bright,_Threshold;

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

        float4 frag(VertexInput input) : COLOR
        {
            float4 texColor = tex2D(_MainTex,input.texCoord.xy);
            if(_Effect == 1)
            {
                texColor.rgb = texColor.r;
            }else if(_Effect == 2)
            {
                texColor.rgb = (texColor.r + texColor.g + texColor.b)/3.0;
            }else if(_Effect == 3)
            {
                texColor.rgb = 0.3*texColor.r + 0.59*texColor.g + 0.11*texColor.b;
            }

            texColor.rgb *= _Bright;

            if(_UseThreshold == 1)
            {
                if(texColor.r >= _Threshold)
                {
                    texColor.r = 1.0;
                }else
                {
                    texColor.r = 0.0;
                }
                if(texColor.g >= _Threshold)
                {
                    texColor.g = 1.0;
                }else
                {
                    texColor.g = 0.0;
                }
                if(texColor.b >= _Threshold)
                {
                    texColor.b = 1.0;
                }else
                {
                    texColor.b = 0.0;
                }
            }

            return texColor;
            
        }
        
        
        
        ENDCG
        }
    }
}
