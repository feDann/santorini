package it.polimi.ingsw.PSP11.messages;

public class BuildBeforeMoveResponse extends Message{

    private boolean canBuildBefore;

    public BuildBeforeMoveResponse(boolean canBuildBefore) {
        super("can he build before moving? -> " + canBuildBefore);
        this.canBuildBefore = canBuildBefore;
    }

    public boolean isCanBuildBefore() {
        return canBuildBefore;
    }
}
