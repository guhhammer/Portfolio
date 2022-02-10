Shader "Custom/HelloShader"
{
    Properties
    {
    
        _sizeX("X", Float) = 1.0
        _sizeY("Y", Float) = 1.0
        _Red("Red", Range(0,1)) = 1.0
        _Green("Green", Range(0,1)) = 1.0
        _Blue("Blue", Range(0,1)) = 1.0
           
    }
    
    SubShader
    {
    
    Pass
    {

        CGPROGRAM
        
        #pragma vertex vert   
        #pragma fragment frag

        float _sizeX, _sizeY;
        float _Red, _Green, _Blue; 


        float4 vert(float4 vertexPos:POSITION) : SV_POSITION
        {

            return UnityObjectToClipPos( float4(_sizeX, _sizeY, 1, 1) * vertexPos );

        }

        
        float4 frag(void) : COLOR
        {

            return float4(_Red, _Green, _Blue, 1);

        }


        ENDCG
    
    }
    
    }
}
