-- Statuswert an den Projektbericht angleichen: ANGENOMMEN heißt jetzt EINGESTELLT
UPDATE bewerbung SET status = 'EINGESTELLT' WHERE status = 'ANGENOMMEN';
