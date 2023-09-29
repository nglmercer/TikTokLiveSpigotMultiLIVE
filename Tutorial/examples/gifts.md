# How to handle Gifts?



```yaml
onGift:
   /title @p title "&7Thank you for ${event.comboCount} &2${event.gift.name}"
                               
   switch event.gift.name 
    case "ItCorn"              
    then /say Thank you for ${event.comboCount} Corns      
   
    case "Rose"                 
    then /say Thank you for Rose
  
    case "Bravo"                
    then /adicionarpontos 100 GTA-5
  
    case "TikTok"               
    then /adicionarpontos 100 APEX-LEGENDS
  
    default                    
    then /say this is some undefined gift!
```