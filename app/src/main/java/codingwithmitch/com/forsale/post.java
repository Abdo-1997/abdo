package codingwithmitch.com.forsale;

public class post {
    private String title;
    private String email;
    private String image;
    //private boolean permission;

    public post() {
    }

    public post(String title, String email, String image, boolean permission) {
        this.title = title;
        this.email = email;
        this.image = image;
        //this.permission = permission;
    }

    public String gettitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

   // public boolean getPermission() {
       // return permission;
   // }

   /* public void setPermission(boolean permission) {
        this.permission = permission;
    }*/
}