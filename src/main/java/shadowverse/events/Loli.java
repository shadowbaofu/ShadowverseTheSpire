package shadowverse.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowverse.reward.LoliReward;

public class Loli extends AbstractImageEvent {
    public static final String ID = "Loli";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("Loli");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_BODY_M = DESCRIPTIONS[0];

    private static final String ACCEPT_BODY = DESCRIPTIONS[2];

    private static final String EXIT_BODY = DESCRIPTIONS[3];


    private int screenNum = 0;

    public Loli() {
        super(NAME, "test", "img/event/Loli.jpg");
        this.body = INTRO_BODY_M;
        this.imageEventText.setDialogOption(OPTIONS[0]);
        this.imageEventText.setDialogOption(OPTIONS[1]);
    }

    @Override
    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_GHOSTS");
        }
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(ACCEPT_BODY);
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addCardReward(new LoliReward());
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        AbstractDungeon.combatRewardScreen.open();
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
                logMetricIgnored("Loli");
                this.imageEventText.updateBodyText(EXIT_BODY);
                this.screenNum = 2;
                this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                this.imageEventText.clearRemainingOptions();
                return;
            case 1:
                openMap();
                return;
        }
        openMap();
    }
}
