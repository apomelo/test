# maven 打包说明

## 定义 resource 目录

定义 resource 目录

include 或 exclude (引用或排除) resource 目录下的资源时可以直接用相对路径

## maven-clean-plugin

用 maven-clean-plugin 插件清除上一次打包结果

1. 是否排除默认编译目录 `<excludeDefaultDirectories>`
1. 指定额外清除的目录
1. [官方文档参数](https://maven.apache.org/plugins/maven-clean-plugin/clean-mojo.html)

## maven-compiler-plugin

用 maven-compiler-plugin 插件编译代码

1. 指定 java 版本
1. 指定 encoding
1. 设置编译参数 `compilerArgs`
1. [官方文档参数](https://maven.apache.org/plugins/maven-compiler-plugin/compile-mojo.html)

## maven-jar-plugin

用 maven-jar-plugin 打不包含任何依赖的独立包

1. 设置 manifest 参数
1. 自定义输出目录
1. 自定义输出包的名称 
1. 排除指定目录
1. [官方文档参数](https://maven.apache.org/plugins/maven-jar-plugin/jar-mojo.html)

## maven-assembly-plugin

用 maven-assembly-plugin 打自定义格式包

1. 设置 manifest 参数
1. 配置 descriptor (assembly 描述文件) 路径
1. 自定义输出目录
1. 自定义输出包的名称
1. 设置打包文件是否带上描述文件的id `<appendAssemblyId>`
1. 配置 execution
1. [官方文档参数](http://maven.apache.org/plugins/maven-assembly-plugin/assembly.html)

### assembly*.xml 文件说明

**易混淆参数说明**
1. `baseDirectory`: 定义包内打包根目录
    - 建议使用 `baseDirectory`, 原因: `baseDirectory` 会覆盖 `includeBaseDirectory`
1. jar 包 `baseDirectory` 建议统一设置成 `/`

#### assembly-jar1-1.xml

1. 用缩写 `groupId:artifactId` 打包不包含依赖的独立 jar 包
1. 包内是 class 文件
1. 包内根路径是: `/`

#### assembly-jar1-2.xml

1. 用全限定符 `groupId:artifactId:type[:classifier]:version` 打包不包含依赖的独立 jar 包
1. 包内是独立 jar 包
1. 包内根路径是: `/${project.name}`

#### assembly-gz1.xml

1. 把编译好的 jar 包, 再打包成 tar.gz 格式, 并且把启动脚本、配置文件、jar 包打包到各自目录
1. 项目 jar 包和依赖的包, 是一个一个独立的包, 统一放在 lib/ 目录下面
1. 包内根路径是: `/`

#### assembly-jar2.xml

1. 把本程序和所有的依赖包打进一个 jar 包
1. 包内是 class 文件
1. 包内根路径是: `/`

#### assembly-gz2.xml

1. 把编译好的 jar 包, 再打包成 tar.gz 格式, 并且把启动脚本、配置文件、jar 包打包到各自目录
1. 项目 jar 包和依赖的包, 是一个包(项目代码和依赖的包打在一个 jar 包里), 放在 lib/ 目录下面
1. 包内根路径是: `/${project.name}`
