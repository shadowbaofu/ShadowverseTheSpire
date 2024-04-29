package shadowverse.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import shadowverse.cards.Neutral.Temp.CrystalBright;
import shadowverse.cards.Neutral.Temp.GemLight;
import shadowverse.cards.Vampire.Basic.RazoryClaw;

import java.util.ArrayList;
import java.util.List;

public class GemFortune extends AbstractImageEvent {
    public static final String ID = "GemFortune";

    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("GemFortune");

    public static final String NAME = eventStrings.NAME;

    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;

    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String INTRO_BODY_M = DESCRIPTIONS[0];

    private static final String ACCEPT_BODY = DESCRIPTIONS[2];

    private static final String EXIT_BODY = DESCRIPTIONS[3];

    private static final float HP_DRAIN = 0.3F;

    private int screenNum = 0, hpLoss = 0;

    public GemFortune() {
        super(NAME, "test", "img/event/GemFortune.png");
        this.body = INTRO_BODY_M;
        this.hpLoss = MathUtils.ceil(AbstractDungeon.player.maxHealth * 0.2F);
        if (this.hpLoss >= AbstractDungeon.player.maxHealth)
            this.hpLoss = AbstractDungeon.player.maxHealth - 1;
        if (AbstractDungeon.ascensionLevel >= 15) {
            this.hpLoss = MathUtils.ceil(AbstractDungeon.player.maxHealth * 0.3F);
            this.imageEventText.setDialogOption(OPTIONS[3] + this.hpLoss + OPTIONS[1], new CrystalBright());
        } else {
            this.imageEventText.setDialogOption(OPTIONS[0] + this.hpLoss + OPTIONS[1], new CrystalBright());
        }
        this.imageEventText.setDialogOption(OPTIONS[2]);
    }

    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_GHOSTS");
    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(ACCEPT_BODY);
                        AbstractDungeon.player.decreaseMaxHealth(this.hpLoss);
                        becomeGhost();
                        this.screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
                logMetricIgnored("GemFortune");
                this.imageEventText.updateBodyText(EXIT_BODY);
                this.screenNum = 2;
                this.imageEventText.updateDialogOption(0, OPTIONS[5]);
                this.imageEventText.clearRemainingOptions();
                return;
            case 1:
                openMap();
                return;
        }
        openMap();
    }

    private void becomeGhost() {
        ArrayList<AbstractCard> masterDeck = AbstractDungeon.player.masterDeck.group;
        int i;
        int amt=0;
        for (i = masterDeck.size() - 1; i >= 0; i--) {
            AbstractCard card = masterDeck.get(i);
            AbstractDungeon.player.masterDeck.removeCard(card);
            amt++;
        }
        List<String> cards = new ArrayList<>();
        if (amt>0){
            for (i = 0; i < amt; i++) {
                if (i < amt * 0.66) {
                    GemLight gemLight = new GemLight();
                    cards.add((gemLight).cardID);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(gemLight, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }else {
                    CrystalBright crystalBright = new CrystalBright();
                    cards.add((crystalBright).cardID);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(crystalBright, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
            }
        }
        logMetricObtainCardsLoseMapHP("GemFortune", "Became a Ghost", cards, this.hpLoss);
    }
}
