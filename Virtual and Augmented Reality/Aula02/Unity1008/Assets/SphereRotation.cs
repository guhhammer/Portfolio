using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NewBehaviourScript : MonoBehaviour
{

    private Vector3 rotation;

    public float velocity = 1.0f;

    void Start()
    {
     
        Debug.Log("########### START FUNCTION");
        
        rotation = this.transform.eulerAngles;

    }

    private void Awake(){ Debug.Log("########### AWAKE FUNCTION"); }


    void Update()
    {   

        rotation += new Vector3(0,0,1) * Time.deltaTime;

        this.transform.eulerAngles = rotation;

    }

    private void FixedUpdate(){ }

}
