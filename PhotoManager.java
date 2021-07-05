public class PhotoManager {
    private BST<LinkedList<Photo>> listBST;

    private LinkedList<Photo> Photos;


    public PhotoManager() {
        listBST = new BST<>();
        Photos = new LinkedList<>();
    }

    public void addPhoto(Photo p) {
        if (isExist(p))
            return;
        LinkedList<String> tags = p.getTags();

        if (Photos.empty())
            Photos.insert(p);
        else {
            while (!Photos.last()) {
                Photos.findFirst();
            }
            Photos.insert(p);
        }

        p.getTags().findFirst();
        while (!tags.empty() && !tags.last()) {

            boolean tagFound = listBST.findKey(tags.retrieve());
            if (tagFound) {
                listBST.retrieve().insert(p);
            } else {
                LinkedList<Photo> list = new LinkedList<>();
                list.insert(p);
                listBST.insert(tags.retrieve(), list);
            }
            tags.findNext();
        }
        if (!tags.empty()) {
            boolean tagFound = listBST.findKey(tags.retrieve());
            if (tagFound) {
                listBST.retrieve().insert(p);
            } else {
                LinkedList<Photo> list = new LinkedList<>();
                list.insert(p);
                listBST.insert(tags.retrieve(), list);
            }
        }

    }

    public void deletePhoto(String path) {

        Photos.findFirst();
        LinkedList<String> tags = new LinkedList<>();

        while (!Photos.empty() && !Photos.last()) {
            if (Photos.retrieve().getPath().equals(path)) {
                tags = Photos.retrieve().getTags();
                Photos.remove();
                break;
            }
            Photos.findNext();
        }
        if (!Photos.empty()) {
            if (Photos.retrieve().getPath().equals(path)) {
                tags = Photos.retrieve().getTags();
                Photos.remove();
            }
        }

        tags.findFirst();
        while (!tags.empty() && !tags.last()) {
            deleteHelper(tags, path);
            tags.findNext();
        }

        if (!tags.empty()) {
            deleteHelper(tags, path);
        }
    }

    private void deleteHelper(LinkedList<String> tags, String path) {
        {
            listBST.findKey(tags.retrieve());
            LinkedList<Photo> list = listBST.retrieve();
            list.findFirst();
            for (; !list.empty() && !list.last(); ) {
                if (path.equals(list.retrieve().getPath())) {
                    list.remove();
                    break;
                }
                list.findNext();
            }

            deleteHelper1(list, path, tags);
        }
    }

    private void deleteHelper1(LinkedList<Photo> list, String path, LinkedList<String> tags) {

        if (!list.empty() && path.equals(list.retrieve().getPath()))
            list.remove();


        if (list.empty())
            listBST.removeKey(tags.retrieve());

        return;
    }


    private boolean isExist(Photo p) {
        boolean bool = false;
        if (!Photos.empty()) {
            Photos.findFirst();
            while (!Photos.last()) {
                if (Photos.retrieve().getPath().equals(p.getPath()))
                    bool = true;
                Photos.findNext();
            }
            if (Photos.retrieve().getPath().equals(p.getPath()))
                bool = true;
        }

        return bool;
    }


    public BST<LinkedList<Photo>> getPhotos(){
        return listBST;
    }
    public LinkedList<Photo> getAllPhotos() {
        return Photos;
    }
}
