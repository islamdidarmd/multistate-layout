# Multistate layout
A custom layout that helps to manage multiple states(LOADING, EMPTY, CONTENT) of the layout. This layout is also aware
about network state.

[![](https://jitpack.io/v/islamdidarmd/multistate-layout.svg)](https://jitpack.io/v/islamdidarmd/multistate-layout)

## Installation (with Gradle)
Add the dependency to your root *build.gradle*:
```groovy
   repositories {
        jcenter()
        maven { url "https://jitpack.io" }
   }
   ```
Now add this dependency in your module *build.gradle*
```groovy
   dependencies {
         implementation 'com.github.islamdidarmd:multistate-layout:$latest_release'
   }
```

### Demo
  <img src="https://github.com/islamdidarmd/Multistate-Layout/blob/master/Screenshot_1552992432.png" width="250"> <img src="https://github.com/islamdidarmd/Multistate-Layout/blob/master/Screenshot_1552992440.png" width="250"> <img src="https://github.com/islamdidarmd/Multistate-Layout/blob/master/Screenshot_1552992443.png" width="250">

### How to use
For using custom attributes use `msl` namespace in the root element of the layout

`xmlns:msl="http://schemas.android.com/apk/res-auto"`

`msl:loadingLayout`, `msl:emptyLayout` are optional. If you don't include these, a basic layout will be shown.

if you set `msl:showConnectionStatus="true"`, then a layout will be shown on top of the layout if device doesn't have a data
connection

```xml
 <com.islamdidarmd.multistatelayout.MultiStateLayout
            android:layout_width="match_parent"
            android:id="@+id/multiStateLayout"
            android:layout_height="match_parent"
            android:layout_above="@id/btnEmpty"
            msl:contentLayout="@id/content"
            msl:loadingLayout="@id/layoutLoading"
            msl:emptyLayout="@id/layoutNoData"
            msl:showConnectionStatus="true"> 
             
            <RelativeLayout
                            android:id="@+id/layoutLoading"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent">     
                       ...
                    </RelativeLayout>
            
                    <RelativeLayout
                            android:id="@+id/layoutNoData"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent">
                       ...
                    </RelativeLayout>
            
                    <RelativeLayout
                            android:id="@+id/content"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent">
            
                        ...
                    </RelativeLayout>
            
                </com.islamdidarmd.multistatelayout.MultiStateLayout>

```

### States
``` 
    State.LOADING,
    State.EMPTY,
    State.CONTENT
```
### Changing States
```kotlin
        btnLoading.setOnClickListener {
            multiStateLayout.setState(MultiStateLayout.State.LOADING)
        }

        btnContent.setOnClickListener {
            multiStateLayout.setState(MultiStateLayout.State.CONTENT)
        }

        btnEmpty.setOnClickListener {
            multiStateLayout.setState(MultiStateLayout.State.EMPTY)
        }
```


For a complete example see the sample app `https://github.com/islamdidarmd/Multistate-Layout/tree/master/app`
