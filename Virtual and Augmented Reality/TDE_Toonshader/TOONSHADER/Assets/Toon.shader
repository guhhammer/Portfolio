Shader "Roystan/Toon"
{
	Properties
	{
		_Color("Color", Color) = (0.5, 0.65, 1, 1)
		_MainTex("Main Texture", 2D) = "white" {}	

        // TOON SHADER
        [HDR]
        _AmbientColor("Ambient Color", Color) = (0.1,0.1,0.1,1)

        [HDR]
        _SpecularColor("Specular Color", Color) = (0.9,0.9,0.9,1)
        _Glossiness("Glossiness", Float) = 32

        [HDR]
        _RimColor("Rim Color", Color) = (1,1,1,1)
        _RimAmount("Rim Amount", Range(0, 1)) = 0.716
	}
	SubShader
	{
        Tags
        {
            "LightMode" = "ForwardBase"
            "PassFlags" = "OnlyDirectional"
        }
		Pass
		{
			CGPROGRAM
			#pragma vertex vert
			#pragma fragment frag
			
			#include "UnityCG.cginc"
            #include "Lighting.cginc"

			struct appdata
			{
				float4 vertex : POSITION;				
				float4 uv : TEXCOORD0;

                // TOON SHADER
                float3 normal : NORMAL;
			};

			struct v2f
			{
				float4 pos : SV_POSITION;
				float2 uv : TEXCOORD0;

                // TOON SHADER
                float3 worldNormal : NORMAL;

                float3 viewDir : TEXCOORD1;
			};

			sampler2D _MainTex;
			float4 _MainTex_ST;
			
			v2f vert (appdata v)
			{
				v2f o;
				o.pos = UnityObjectToClipPos(v.vertex);
				o.uv = TRANSFORM_TEX(v.uv, _MainTex);

                // TOON SHADER
                o.worldNormal = UnityObjectToWorldNormal(v.normal);
                o.viewDir = WorldSpaceViewDir(v.vertex);

				return o;
			}
			
			float4 _Color;

            // TOON SHADER
            float4 _AmbientColor;

            float _Glossiness;
            float4 _SpecularColor;

            float4 _RimColor;
            float _RimAmount;

			float4 frag (v2f i) : SV_Target
			{
				float4 sample = tex2D(_MainTex, i.uv);

                // TOON SHADER

                // Normalizacao de Cor e Sombra
                float3 normal = normalize(i.worldNormal);
                float NdotL = dot(_WorldSpaceLightPos0, normal);

                float lightIntensity = NdotL > 0 ? 1 : 0; 

                float4 light = lightIntensity * _LightColor0;

                // Reflexo especular
                float3 viewDir = normalize(i.viewDir);

                float3 halfVector = normalize(_WorldSpaceLightPos0 + viewDir);
                float NdotH = dot(normal, halfVector);

                float specularIntensity = pow(NdotH * lightIntensity, _Glossiness * _Glossiness);

                float specularIntensitySmooth = smoothstep(0.005, 0.01, specularIntensity);
                float4 specular = specularIntensitySmooth * _SpecularColor;

                // Borda
                float4 rimDot = 1 - dot(viewDir, normal);

                float rimIntensity = smoothstep(_RimAmount - 0.01, _RimAmount + 0.01, rimDot);
                //float4 rim = rimIntensity * _RimColor;

                float4 rim = rimIntensity > (_RimAmount + 1) ? float4(1,1,1,1) : (rimIntensity * _RimColor);

                // TOON SHADER
                if (lightIntensity == 0 || all(rim == float4(1,1,1,1))) {
                    return _Color * sample * (_AmbientColor + 0);
                }
                else {
                    return _Color * sample * (_AmbientColor + lightIntensity + specular + rim);                 
                }

                //return _Color * sample * (_AmbientColor + lightIntensity + specular + rim);
			}
			ENDCG
		}
	}
}