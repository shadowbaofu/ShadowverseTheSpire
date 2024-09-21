package shadowverse.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import shadowverse.characters.*;
import shadowverse.reward.TransReward;

import java.util.ArrayList;
import java.util.Arrays;

public class ShadowverseEvolve  extends AbstractImageEvent {
    public static final String ID = "ShadowverseEvolve";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("ShadowverseEvolve");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_BODY_M = DESCRIPTIONS[0];

    private static final String ACCEPT_BODY = DESCRIPTIONS[1];

    private static final String EXIT_BODY = DESCRIPTIONS[2];


    private int screenNum = 0;

    public ShadowverseEvolve() {
        super(NAME, "test", "img/event/ShadowverseEvolve.jpg");
        this.body = INTRO_BODY_M;
        if(isSVP()){
            this.imageEventText.setDialogOption(OPTIONS[0]);

        }else{
            this.imageEventText.setDialogOption(OPTIONS[1]);

        }
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public boolean isSVP(){
        ArrayList<AbstractPlayer.PlayerClass> cls=new ArrayList<>(Arrays.asList(Elf.Enums.Elf, Royal.Enums.Royal, Witchcraft.Enums.WITCHCRAFT, Dragon.Enums.Dragon, Necromancer.Enums.Necromancer,Vampire.Enums.Vampire,Bishop.Enums.Bishop));
        return cls.stream().anyMatch(playerClass -> playerClass == AbstractDungeon.player.chosenClass);
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
                        if(isSVP()){
                            this.imageEventText.updateBodyText(ACCEPT_BODY);
                            AbstractDungeon.getCurrRoom().rewards.clear();
                            AbstractDungeon.getCurrRoom().addCardReward(new TransReward());
                            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                            AbstractDungeon.combatRewardScreen.open();
                            this.screenNum = 2;
                            this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                            this.imageEventText.clearRemainingOptions();
                            return;
                        }else{
                            this.imageEventText.updateBodyText(ACCEPT_BODY);
                            AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[7], false, false, false, true);
                            this.screenNum = 2;
                            this.imageEventText.updateDialogOption(0, OPTIONS[2]);
                            this.imageEventText.clearRemainingOptions();
                        }
                }
                logMetricIgnored("SVE");
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

    public void update() {
        super.update();
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.gridSelectScreen.selectedCards.remove(c);
        }

    }
}
