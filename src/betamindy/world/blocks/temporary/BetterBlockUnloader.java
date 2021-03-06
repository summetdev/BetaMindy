package betamindy.world.blocks.temporary;

import mindustry.gen.*;
import mindustry.type.*;

import static mindustry.Vars.*;

//TODO: Will be removed when Anuke publishes next release(current: v122)
public class BetterBlockUnloader extends BetterBlockLoader{

    public BetterBlockUnloader(String name){
        super(name);
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    public class BlockUnloaderBuild extends BlockLoaderBuild{

        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }

        @Override
        public void updateTile(){
            if(shouldExport()){
                moveOutPayload();
            }else if(moveInPayload()){

                //load up items
                if(payload.block().hasItems && !full()){
                    if(efficiency() > 0.01f && timer(timerLoad, loadTime / efficiency())){
                        //load up items a set amount of times
                        for(int j = 0; j < itemsLoaded && !full(); j++){
                            for(int i = 0; i < items.length(); i++){
                                if(payload.build.items.get(i) > 0){
                                    Item item = content.item(i);
                                    payload.build.items.remove(item, 1);
                                    items.add(item, 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            dump();
        }

        public boolean full(){
            return items.total() >= itemCapacity;
        }

        @Override
        public float fraction(){
            return payload == null ? 0f : 1f - payload.build.items.total() / (float)payload.build.block.itemCapacity;
        }

        @Override
        public boolean shouldExport(){
            return payload != null && (payload.block().hasItems && payload.build.items.empty());
        }
    }
}
