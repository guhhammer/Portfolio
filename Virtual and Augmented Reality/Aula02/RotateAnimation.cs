using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RotateAnimation : MonoBehaviour
{
    private Vector3 rotation;

    public float velocity = 1.0f;
    // Start is called before the first frame update
    void Start()
    {
        Debug.Log("Start called");
        rotation = this.transform.eulerAngles;
    }

    private void Awake()
    {
        Debug.Log("Awake called");
    }

    // Update is called once per frame
    void Update()
    {
        
        rotation += new Vector3(0,0,1) * Time.deltaTime;
        this.transform.eulerAngles = rotation;
    }

    private void FixedUpdate()
    {
    }
}
