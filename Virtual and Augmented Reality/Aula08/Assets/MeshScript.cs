using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MeshScript : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
        gameObject.AddComponent<MeshFilter>();
        
        Mesh mesh = gameObject.GetComponent<MeshFilter>().mesh;
        /*
        mesh.vertices = new Vector3[]
        {
            new Vector3(-2, 0, -3),
            new Vector3(0, 4, -3),
            new Vector3(3, 0, -3)
        };

        mesh.triangles = new int[]
        {
            0, 1, 2
        };


        mesh.colors = new Color[]
        {
            new Color(1, 0, 0, 1),
            new Color(0, 1, 0, 1),
            new Color(0, 0, 1, 1)
        };*/

        mesh.vertices = new Vector3[]
        {
            new Vector3(-2, 0, -3),
            new Vector3(-2, 4, -3),
            new Vector3(2, 4, -3),
            new Vector3(2, 0, -3)

        };

        mesh.triangles = new int[]
        {
            0, 1, 2,
            2, 3, 0
        };

        mesh.colors = new Color[]
        {
            new Color(1, 0, 0, 1),
            new Color(0, 1, 0, 1),
            new Color(0, 0, 1, 1),
            new Color(0, 1, 1, 1)

        };

        gameObject.AddComponent<MeshRenderer>();
        Renderer renderer = gameObject.GetComponent<MeshRenderer>().GetComponent<Renderer>();
        
        renderer.material = new Material(Shader.Find("Custom/ColorShader"));

    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
