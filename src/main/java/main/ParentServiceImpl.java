public class ParentServiceImpl implements ParentService {
    @Override
    public void addParentService(Parent p) {
        System.out.println("用户数据成功入库"+p.getName());
    }
}
