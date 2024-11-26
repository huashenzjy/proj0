/**
 * @Author: huashen
 * @CreateTime: 2024-11-25  19:45
 * @Description: TODO
 * @Version: 1.0
 **/
public class Planet {
    public double F;
    public double Fx;
    public double Fy;
    public double dx;
    public double dy;
    public double r;
    public static final double G = 6.67e-11;

    // 属性
    public double xxPos;
    public double yyPos;
    //position位置
    public double xxVel;
    //Its current velocity in the x direction
    // x方向上的速度
    public double yyVel;//Its current velocity in the y direction
    public double mass;//Its mass
    public String imgFileName;
    //The name of the file that corresponds to the image that
    // depicts the planet (for example, jupiter.gif)
    //与描绘行星的图像对应的文件名（例如，jupiter.gif

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    //The second constructor should take in a Planet object and initialize an identical Planet object
    // (i.e. a copy). The signature of the second constructor should be:
    //第二个构造函数应该接受一个 Planet 对象并初始化一个相同的 Planet 对象
    // （即一个副本）。第二个构造函数的签名应为：
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    //该方法将接收单个行星，并返回一个double
//等于所提供的行星与进行计算的行星之间的距离
//即返回 r
    public double calDx(Planet planet) {
        //规定， 向右为正方向
        // 求某个星球，对自己施加的力
        // 所以用planet.xxPos - xxPos
        dx = planet.xxPos - this.xxPos;
        return dx;
    }

    public double calDy(Planet planet) {
        dy = planet.yyPos - this.yyPos;
        return dy;
    }

    public double calcDistance(Planet planet) {
        double rsqr;
        // 先确定dx
        dx = this.calDx(planet);
        dy = this.calDy(planet);
        rsqr = dx * dx + dy * dy;
        return Math.sqrt(rsqr);
    }

    public double calcForceExertedBy(Planet planet) {
        // 返回合力
        //科学计数法：5.12E2（既5.12*10^2读作：5.12乘10的2次方），
        // 且E是不区分大小写的，也可以写作5.12e2。
        // 只有浮点型的数字才能直接使用科学计数法形式表示。
        r = this.calcDistance(planet);
        F = G * this.mass * planet.mass / r / r;
        return F;
    }

    public double calcForceExertedByX(Planet planet) {
        F = this.calcForceExertedBy(planet);
        r = this.calcDistance(planet);
        this.calDx(planet);
        Fx = F * dx / r;
        return Fx;
    }

    public double calcForceExertedByY(Planet planet) {
        F = this.calcForceExertedBy(planet);
        r = this.calcDistance(planet);
        this.calDy(planet);
        Fy = F * dy / r;
        return Fy;
    }

    //each take in an array of Planets and calculate the net X and net Y force exerted by all planets
// in that array upon the current Planet
    // 求别人对自己的合力，this为自己， planet为别人
    public double calcNetForceExertedByX(Planet[] allplanets) {
// 接收了一个数组
        // 返回一个合力
        double totalForceX = 0;
        for (Planet planet : allplanets) {
            if (!this.equals(planet)) {
                //如果不相等，就开始工作
                //先判断坐标是怎么样
                dx = this.calDx(planet);
                r = this.calcDistance(planet);
                F = this.calcForceExertedBy(planet);
                Fx = F * dx / r;
                totalForceX += Fx;
            }
        }
        return totalForceX;
    }

    public double calcNetForceExertedByY(Planet[] allplanets) {
// 接收了一个数组
        // 返回一个合力
        double totalForceY = 0;
        for (Planet planet : allplanets) {
            if (!this.equals(planet)) {
                //如果不相等，就开始工作
                //先判断坐标是怎么样
                dy = this.calDy(planet);
                r = this.calcDistance(planet);
                F = this.calcForceExertedBy(planet);
                Fy = F * dy / r;
                totalForceY += Fy;
            }
        }
        return totalForceY;
    }

    public void update(double dt, double fx, double fy) {
        double ax;// x加速度
        double ay;// y加速度
        double VnewX;
        double VnewY;
        double vOldX = this.xxVel;
        double vOldy = this.yyVel;
        ax = fx / this.mass;
        ay = fy / this.mass;
        VnewX = vOldX + dt * ax;// VnewX;
        VnewY = vOldy + dt * ay; //VnewY
        this.xxVel = VnewX;
        this.yyVel = VnewY;
        this.xxPos = this.xxPos + dt * VnewX;
        this.yyPos = this.yyPos + dt * VnewY;
    }
    public void draw(){
        // 画出他自己的图片
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
