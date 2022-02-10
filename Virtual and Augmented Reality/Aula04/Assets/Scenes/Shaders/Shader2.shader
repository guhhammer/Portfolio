Shader "Custom/Shader2"
{
    
    Properties
    {
        
        _Hue("Hue", Range(0.0, 360.0)) = 0.0
        _Saturation("Saturation", Range(0, 1)) = 1.0
        _Value("Value", Range(0, 1)) = 1.0

        _sizeX("X", Range(0, 1)) = 1.0
        _sizeY("Y", Range(0, 1)) = 1.0


    }

    SubShader
    {

        Pass
        {

        CGPROGRAM
        
        #pragma vertex vert
        #pragma fragment frag

        float _Hue, _Saturation, _Value, _sizeX, _sizeY;

        float4 vert(float4 vertexPos:POSITION) : SV_POSITION
        {

            return UnityObjectToClipPos( float4(_sizeX, _sizeY, 1, 1) * vertexPos );

        }
    

        float4 frag(void) : COLOR
        {

            float r, g, b;

            float _Abs = ((_Hue / 60) % 2) - 1;
            if( ((int) _Abs) < 0){ 

                _Abs = -1 * _Abs;

            }


            float _C = _Value * _Saturation;
            float _X = _C * _Abs;
            float _M = _Value - _C;

            if(_Hue < 60){ r = _C; g = _X; b = 0;}
            else if(_Hue < 120){ r = _X; g = _C; b = 0; }
            else if(_Hue < 180){ r = 0; g = _C; b = _X; }
            else if(_Hue < 240){ r = 0; g = _X; b = _C; }
            else if(_Hue < 300){ r = _X; g = 0; b = _C; }
            else{ r = _C; g = 0; b = _X; }

            return float4( (r + _M), (g + _M), (b + _M), 1);

        }

    
        ENDCG
    
        }

    }
    
}
