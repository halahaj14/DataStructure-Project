package pa.pkg2.pkg2;


public class Album {

    private String name;
    private String condition;
    private PhotoManager manger;

    private Album() {
    }

    public Album(String name, String condition, PhotoManager photoManager) {
        this.name = name;
        this.condition = condition;
        this.manger = photoManager;
    }

    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public PhotoManager getManger() {
        return manger;
    }

    public LinkedList<Photo> getPhotos(){
        LinkedList<Photo> list = new LinkedList<>();
        if(condition == null || condition.length() ==0)
            list = manger.getAllPhotos();
        else{
            String[] condTag = split(" AND ");
            LinkedList<Photo> temp = manger.getAllPhotos();
            temp.findFirst();
            while(!temp.empty() && !temp.last()){
                Photo photo = temp.retrieve();
                boolean found = true;
                for(int i =0;i<condTag.length;i++){
                    if(!tagFinder(photo,condTag[i]))
                        found = false;
                }
                if(found)
                    list.insert(photo);
                temp.findNext();
            }
            getHelper(list, condTag, temp);
        }
        return list;
    }

    private void getHelper(LinkedList<Photo> list, String[] condTag, LinkedList<Photo> temp) {
        if(!temp.empty()){
            Photo photo = temp.retrieve();
            boolean found = true;
            for(int i =0;i<condTag.length;i++){
                if(!tagFinder(photo,condTag[i]))
                    found = false;
            }
            if(found)
                list.insert(photo);
        }
    }

    public int getNbComps() {
        int x = 0;
        if (condition != null && condition.length() != 0) {
            String[] tags = split(" AND ");
            for (int i = 0; i < tags.length; i++)
                x = x + manger.getPhotos().getNumberOfComparisons(tags[i]);
        }
        return x;
    }
  private boolean checkRepetition(LinkedList<Photo> photos, Photo photo) {
        if (photos.empty())
            return false;
        photos.findFirst();
        while (!photos.last()) {
            if (photos.retrieve().getPath().equals(photo.getPath()))
                return true;
            photos.findNext();
        }
        if (photos.retrieve().getPath().equals(photo.getPath()))
            return true;
        return false;
    }


    private boolean tagFinder(Photo photo, String string) {
        LinkedList<String> tags = photo.getTags();
        tags.findFirst();
        while(!tags.empty() && !tags.last()){
            if(tags.retrieve().equals(string))
                return true;
            tags.findNext();
        }
        if (finderhelper(string, tags)) return true;
        return false;
    }

    private boolean finderhelper(String string, LinkedList<String> tags) {
        if(!tags.empty()){
            if(tags.retrieve().equals(string))
                return true;
        }
        return false;
    }

    private String[] split(String s) {
        String[] tags = this.condition.split(s);
        return tags;
    }
}
