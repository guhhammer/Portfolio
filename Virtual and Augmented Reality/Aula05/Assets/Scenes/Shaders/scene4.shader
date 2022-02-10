Shader "Custom/scene4"
{
    Properties
    {
        _MainTex ("Texture", 2D) = "white" {}
        _Mix ("Mix", Float) = 0.3
        _Steps ("Steps", Int) = 1

        [Enum(None,0, BlurMediana,1, BlurCruz,2, BlurNitido,3, Laplace,4, Sharp,5, Emboss,6, Sobel,7)] _Effect("Effect",Int) = 0
    
    }
    SubShader
    {        
        Pass
        {
            CGPROGRAM
            #pragma vertex vert
            #pragma fragment frag

            #include "UnityCG.cginc"
            
            uniform float _Mix;
            uniform float _Steps;
            uniform float4 _MainTex_TexelSize;

            sampler2D _MainTex;
            int _Effect;

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

            // Nothing special here
            v2f vert (appdata v)
            {
                v2f o;
                o.vertex = UnityObjectToClipPos(v.vertex);
                o.uv = v.uv;
                return o;
            }


            fixed4 frag(v2f i) : COLOR
            {

                    float2 texel = float2(_MainTex_TexelSize.x * _Steps, _MainTex_TexelSize.y * _Steps);
                    


                    // https://github.com/Peroshki/Unity-Image-Kernels/tree/master/Assets
                    //
                    // get kernels from slide on bb
                    //
                    // https://en.wikipedia.org/wiki/Sobel_operator || sobel has another calculation phase.
                    //
                    // check variations of the blur in the first link
                    //check kernels in slide, see if they match.
                    // check math pragma on hslg glg



                    float3x3 krnl;                    

                    //None
                    if(_Effect==0){ krnl = float3x3( 0, 0, 0, 0, 0, 0, 0, 0, 0 ); }

                    //BlurMediana
                    if(_Effect==1){ krnl = float3x3( 1/9, 1/9, 1/9, 1/9, 1/9, 1/9, 1/9, 1/9, 1/9 ); }

                    //BlurCruz
                    if(_Effect==2){ krnl = float3x3( 0, 0, 1/5, 1/5, 1/5, 1/5, 1/5, 0, 0 ); }

                    //BlurNitido
                    if(_Effect==3){ krnl = float3x3( 1/16, 1/8, 1/16, 1/8, 1/4, 1/8, 1/16, 1/8, 1/16 ); }

                    //Laplace
                    if(_Effect==4){ krnl = float3x3( 0, 1, 0, 1, -4, 1, 0, 1, 0 ); }

                    //Sharp
                    if(_Effect==5){ krnl = float3x3( 0, -1, 0, -1, 5, -1, 0, -1, 0 ); }

                    //Emboss
                    if(_Effect==6){ krnl = float3x3( -2, -1, 0, -1, 0, 1, 0, 1, 2 ); }

                    //Sobel
                    if(_Effect==7){ krnl = float3x3( -1, 0, 1, -2, 0, 2, -1, 0, 1 ); }


                    float tx0y0 = tex2D( _MainTex, i.uv + texel * float2( -1, -1 ) ).r;
                    float tx0y1 = tex2D( _MainTex, i.uv + texel * float2( -1,  0 ) ).r;
                    float tx0y2 = tex2D( _MainTex, i.uv + texel * float2( -1,  1 ) ).r;

                    float tx1y0 = tex2D( _MainTex, i.uv + texel * float2(  0, -1 ) ).r;
                    float tx1y1 = tex2D( _MainTex, i.uv + texel * float2(  0,  0 ) ).r;
                    float tx1y2 = tex2D( _MainTex, i.uv + texel * float2(  0,  1 ) ).r;

                    float tx2y0 = tex2D( _MainTex, i.uv + texel * float2(  1, -1 ) ).r;
                    float tx2y1 = tex2D( _MainTex, i.uv + texel * float2(  1,  0 ) ).r;
                    float tx2y2 = tex2D( _MainTex, i.uv + texel * float2(  1,  1 ) ).r;

                    float G = krnl[0][0] * tx0y0 + krnl[1][0] * tx1y0 + krnl[2][0] * tx2y0 + 
                            krnl[0][1] * tx0y1 + krnl[1][1] * tx1y1 + krnl[2][1] * tx2y1 + 
                            krnl[0][2] * tx0y2 + krnl[1][2] * tx1y2 + krnl[2][2] * tx2y2;
                    
                    if (_Effect==7){

                        krnl = float3x3( 1, 2, 1, 0, 0, 0, -1, -2, -1 ); 

                        tx0y0 = tex2D( _MainTex, i.uv + texel * float2( -1, -1 ) ).r;
                        tx0y1 = tex2D( _MainTex, i.uv + texel * float2( -1,  0 ) ).r;
                        tx0y2 = tex2D( _MainTex, i.uv + texel * float2( -1,  1 ) ).r;

                        tx1y0 = tex2D( _MainTex, i.uv + texel * float2(  0, -1 ) ).r;
                        tx1y1 = tex2D( _MainTex, i.uv + texel * float2(  0,  0 ) ).r;
                        tx1y2 = tex2D( _MainTex, i.uv + texel * float2(  0,  1 ) ).r;

                        tx2y0 = tex2D( _MainTex, i.uv + texel * float2(  1, -1 ) ).r;
                        tx2y1 = tex2D( _MainTex, i.uv + texel * float2(  1,  0 ) ).r;
                        tx2y2 = tex2D( _MainTex, i.uv + texel * float2(  1,  1 ) ).r;

                        float Gy = krnl[0][0] * tx0y0 + krnl[1][0] * tx1y0 + krnl[2][0] * tx2y0 + 
                                   krnl[0][1] * tx0y1 + krnl[1][1] * tx1y1 + krnl[2][1] * tx2y1 + 
                                   krnl[0][2] * tx0y2 + krnl[1][2] * tx1y2 + krnl[2][2] * tx2y2;
                    
                        G = 10 * (G * G + Gy * Gy) / ((G + Gy) * (G + Gy));

                    }

                    float4 edgePix = float4(G, G, G, 1.0); 
                    float4 texPix = tex2D(_MainTex, i.uv);
                    
                    float4 frag = lerp(texPix, edgePix, _Mix); 
                    return frag;

            }

            ENDCG
        }

    }

}
